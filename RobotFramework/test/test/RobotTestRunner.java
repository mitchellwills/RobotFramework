package test;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import org.jmock.*;
import org.jmock.auto.internal.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runner.notification.*;
import org.junit.runners.*;
import org.junit.runners.model.*;

import com.google.inject.*;

/*
 * some source taken from
 * org.junit.runners.Parameterized
 */

public class RobotTestRunner extends Suite { 
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface DefaultTestParameter {
		//
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface TestParameter {
		String name() default "";
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface TestInjectModule {
		//
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ParamTest {
		//
	}

	private class TestClassRunnerForRobotTests extends BlockJUnit4ClassRunner {
		private final Object[] params;
		private final String name;
		TestClassRunnerForRobotTests(Class<?> type, Object[] params, String name) throws InitializationError {
			super(type);
			this.params = params;
			this.name = name;
		}

		@Override
		public Object createTest() throws Exception {
			Object test = getTestClass().getOnlyConstructor().newInstance(params);
			Mockery context = getMock(test);
			if(context!=null)
				new Mockomatic(context).fillIn(test);
			Injector injector = Guice.createInjector(getInjectionModules(test));
			injector.injectMembers(test);

			return test;
		}

		@Override
		protected String getName() {
			return name;
		}

		@Override
		protected String testName(final FrameworkMethod method) {
			return method.getName() + " [" + getName() + "]";
		}

		@Override
		protected void validateConstructor(List<Throwable> errors) {
			validateOnlyOneConstructor(errors);
		}

		@Override
		protected Statement classBlock(RunNotifier notifier) {
			return childrenInvoker(notifier);
		}

		@Override
		protected Annotation[] getRunnerAnnotations() {
			return new Annotation[0];
		}

		@Override
		protected List<FrameworkMethod> computeTestMethods() {
			return getTestClass().getAnnotatedMethods(ParamTest.class);
		}
	}
	private class DefaultTestClassRunnerForRobotTests extends TestClassRunnerForRobotTests {
		DefaultTestClassRunnerForRobotTests(Class<?> type, Object[] params) throws InitializationError {
			super(type, params, type.getName());
		}

		@Override
		protected List<FrameworkMethod> computeTestMethods() {
			return getTestClass().getAnnotatedMethods(Test.class);
		}
	}

	private final List<Runner> runners = new ArrayList<Runner>();
	public RobotTestRunner(Class<?> klass) throws Throwable {
		super(klass, Collections.<Runner> emptyList());
		runners.addAll(getParamRunners());
		Runner defaultRunner = getDefaultRunner();
		if(defaultRunner!=null)
			runners.add(defaultRunner);
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}
	protected List<Runner> getParamRunners() throws Exception{
		List<Runner> runners = new ArrayList<Runner>();
		List<FrameworkField> fields = getTestClass().getAnnotatedFields(TestParameter.class);
		for (FrameworkField each : fields) {
			int modifiers = each.getField().getModifiers();
			if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers)) {
				Object[] params = (Object[]) each.get(null);
				TestParameter parameterData = each.getField().getAnnotation(TestParameter.class);
				String testName = parameterData.name();
				if (testName.isEmpty())
					testName = each.getName();
				if(getTestClass().getAnnotatedMethods(ParamTest.class).size()==0)
					throw new Exception("No param test found");
				runners.add(new TestClassRunnerForRobotTests(getTestClass().getJavaClass(), params, testName));
			} else
				throw new Exception("Test parameter field must be public static final [" + each.getName() + "]");
		}
		if(runners.size()==0 && getTestClass().getAnnotatedMethods(ParamTest.class).size()!=0)
			throw new Exception("Expected Test parameters for Param tests");
		return runners;
	}

	protected Runner getDefaultRunner() throws Exception {
		List<FrameworkField> fields = getTestClass().getAnnotatedFields(DefaultTestParameter.class);
		if (fields.size() == 0){
			if(getTestClass().getAnnotatedMethods(Test.class).size()!=0 && getTestClass().getAnnotatedFields(TestParameter.class).size()!=0)
				throw new Exception("Expected DefaultTestParameter with Test parameters");
			if(getTestClass().getAnnotatedMethods(Test.class).size()!=0)
				return new DefaultTestClassRunnerForRobotTests(getTestClass().getJavaClass(), new Object[0]);
			return null;
		}
		if (fields.size() != 1)
			throw new Exception("Only one set of default test parameters is allowed");
		
		FrameworkField field = fields.get(0);
		int modifiers = field.getField().getModifiers();
		if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers)) {
			Object[] params = (Object[]) field.get(null);
			if(getTestClass().getAnnotatedMethods(Test.class).size()==0)
				throw new Exception("No default test found");
			return new DefaultTestClassRunnerForRobotTests(getTestClass().getJavaClass(), params);
		}
		throw new Exception("Default test parameter field must be public static final");
	}


	private List<Module> getInjectionModules(Object object) throws Exception {
		List<Module> modules = new ArrayList<Module>();
		List<FrameworkField> fields = getTestClass().getAnnotatedFields(TestInjectModule.class);
		for (FrameworkField each : fields) {
			int modifiers = each.getField().getModifiers();
			if (Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers)) {
				Module module = (Module) each.get(object);
				modules.add(module);
			} else
				throw new Exception("Test inject module field must be public final [" + each.getName() + "]");
		}
		return modules;
	}

	private Mockery getMock(Object object) throws Exception {
		Class<?> testClass = object.getClass();
		Mockery mockery = null;
		for (Field each : testClass.getFields()) {
			if(Mockery.class.isAssignableFrom(each.getType())){
				if(each.getAnnotation(Rule.class) != null)
					throw new Exception("The mockery should not be a rule as it will be handeld by the runner");
				int modifiers = each.getModifiers();
				if (Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers)) {
					if(mockery!=null)
						throw new Exception("Only one mockery field is allowed");
					mockery = (Mockery) each.get(object);
					if(mockery==null)
						throw new Exception("Test mockery field must not be null [" + each.getName() + "]");
				}
				else
					throw new Exception("Test mockery field must be public final and not static [" + each.getName() + "]");
			}
		}
		return mockery;
	}

	@Override
	protected String getName() {
		return getTestClass().getName();
	}
}
