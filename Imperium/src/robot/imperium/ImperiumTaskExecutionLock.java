package robot.imperium;

public final class ImperiumTaskExecutionLock<T, E extends Throwable> {
	private final Object waitLock = new Object();// locked while an object is
													// waiting for a result
	private boolean done = false;
	private E executionException;
	private T returnValue;

	public boolean waitOn(long timeout) {
		synchronized (waitLock) {
			synchronized (this) {
				done = false;
				returnValue = null;
				executionException = null;
				try {
					wait(timeout);
					if(done)
						return true;
					return false;
				} catch (InterruptedException e) {
					return false;
				}
			}
		}
	}

	public void finish(E exception) {
		synchronized (this) {
			returnValue = null;
			executionException = exception;
			done = true;
			notifyAll();
		}
	}

	public void finish(T returnValue) {
		synchronized (this) {
			this.returnValue = returnValue;
			executionException = null;
			done = true;
			notifyAll();
		}
	}

	public T getReturnValue() {
		synchronized (waitLock) {
			return returnValue;
		}
	}

	public E getException() {
		synchronized (waitLock) {
			return executionException;
		}
	}

	public boolean isError() {
		synchronized (waitLock) {
			return executionException != null;
		}
	}
}
