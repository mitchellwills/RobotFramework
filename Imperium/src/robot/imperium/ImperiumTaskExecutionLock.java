package robot.imperium;

/**
 * @author Mitchell
 * 
 * A lock that can be used to block until an asynchronous response to an asynchronous request
 *
 * @param <T> the type of the return value
 * @param <E> exception type that will be thrown
 */
public final class ImperiumTaskExecutionLock<T, E extends Throwable> {
	private final Object waitLock = new Object();// locked while an object is
													// waiting for a result
	private boolean done = false;
	private E executionException;
	private T returnValue;

	/**
	 * Wait for that task to complete
	 * @param timeout timeout to wait before returning false
	 * @return true if task finished before the timeout
	 */
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

	/**
	 * Called upon the end of the response if an exception occured
	 * @param exception
	 */
	public void finish(E exception) {
		synchronized (this) {
			returnValue = null;
			executionException = exception;
			done = true;
			notifyAll();
		}
	}

	/**
	 * Called upon the end of the response upon sucess with the return value
	 * @param returnValue
	 */
	public void finish(T returnValue) {
		synchronized (this) {
			this.returnValue = returnValue;
			executionException = null;
			done = true;
			notifyAll();
		}
	}

	/**
	 * @return get the value returned
	 */
	public T getReturnValue() {
		synchronized (waitLock) {
			return returnValue;
		}
	}

	/**
	 * @return the exception thrown by the response
	 */
	public E getException() {
		synchronized (waitLock) {
			return executionException;
		}
	}

	/**
	 * @return true if an error occured
	 */
	public boolean isError() {
		synchronized (waitLock) {
			return executionException != null;
		}
	}
}
