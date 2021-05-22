package com.nabobsite.test.timerTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import com.nabobsite.modules.nabob.api.pool.trigger.TriggerThread;
import com.nabobsite.modules.nabob.api.pool.trigger.TriggerQueueManagerImpl;
import org.junit.Test;

public class TestTimeOutManagerImpl {
	private static class TO implements TriggerThread {
		private long timeOut;
		private String userId;
		private CountDownLatch latch;

		private TO(long timeOut,String userId,CountDownLatch latch){
			this.timeOut = timeOut;
			this.userId = userId;
		}
		@Override
		public String getUserId() {
			return userId;
		}

		@Override
		public long getTimeOut() {
			return timeOut;
		}

		@Override
		public void run() {
			System.out.println(System.currentTimeMillis()+" run "+userId);
			latch.countDown();
		}
	}

	@Test
	public void test() {
		try {
			TriggerQueueManagerImpl manager = new TriggerQueueManagerImpl();
			List<TriggerThread> tasks = new ArrayList<TriggerThread>();
			int size = 5;
			System.out.println(System.currentTimeMillis());
			CountDownLatch latch = new CountDownLatch(size);
			for(int i=1; i < size+1; i++){
				TriggerThread task = new TO(i*2, String.valueOf(i),latch);
				manager.submit(task);
				tasks.add(task);
			}
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
