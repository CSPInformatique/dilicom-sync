package com.cspinformatique.concurrent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executor {
	private static final Logger logger = LoggerFactory
			.getLogger(Executor.class);

	private int calculatedMaxThreadCount;
	private long dataProcessed;
	private int maxThreadCount;
	private int maxTaskBufferSize;
	private String processName;
	private Date startDate;
	private boolean taskPublicationCompleted;
	private List<Task> tasksToProcess;
	private int threadCount;
	
	private DecimalFormat decimalFormat;

	public Executor(String processName){
		this(processName, 50, 100);
	}
	
	public Executor(String processName, int maxThreadCount, int maxTaskBufferSize) {
		this.processName = processName;
		this.maxTaskBufferSize = maxTaskBufferSize;
		this.maxThreadCount = maxThreadCount;
		
		this.calculatedMaxThreadCount = maxThreadCount / 2;
		
		this.decimalFormat = new DecimalFormat("0.0");
		
		this.startDate = new Date();
		this.tasksToProcess = new ArrayList<Task>();
	}
	
	public void start(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(!taskPublicationCompleted){
					if(threadCount < calculatedMaxThreadCount && tasksToProcess.size() > 0){
						Task taskToProcess = tasksToProcess.get(0);
						
						executeTask(taskToProcess);
						
						tasksToProcess.remove(0);
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException interruptedEx) {
						logger.error("Executor has encountered an error.", interruptedEx);
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					do{
						double dataProcessedBySecAverage = ((double)dataProcessed / (new Date().getTime()
								- startDate.getTime())) * 1000;
						
						
						Thread.sleep(30 * 1000);
							
						double newDataProcessedBySecAverage = ((double)dataProcessed / (new Date().getTime()
								- startDate.getTime())) * 1000 * 60 * 60;
						
						if(dataProcessedBySecAverage < newDataProcessedBySecAverage && calculatedMaxThreadCount < maxThreadCount){
							++calculatedMaxThreadCount;
						}else{
							--calculatedMaxThreadCount;
						}
	
						logger.info(processName + " currently processing " + decimalFormat.format(newDataProcessedBySecAverage) + " data per hour with " + threadCount + " thread(s).");
					}while(!taskPublicationCompleted);
				} catch (InterruptedException interruptedEx) {
					throw new RuntimeException(interruptedEx);
				}
			}
		}).start();
	}
	
	public void waitForCompletion(){
		taskPublicationCompleted = true;
		
		while(tasksToProcess.size() > 0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException interruptedEx) {
				throw new RuntimeException(interruptedEx);
			}
		}
	}

	private void executeTask(final Task task) {
		++threadCount;

		new Thread(new Runnable() {
			@Override
			public void run() {
				dataProcessed += task.execute();

				--threadCount;
			}
		}).start();
	}
	
	public void publishNewTask(Task task){
		boolean taskAdded = false;
		
		do{
			if(this.tasksToProcess.size() < maxTaskBufferSize){
				this.tasksToProcess.add(task);
				taskAdded = true;
			}else{
				try {
					Thread.sleep(200);
				} catch (InterruptedException interruptedEx) {
					throw new RuntimeException(interruptedEx);
				}
			}
		}while(!taskAdded);
	}
}
