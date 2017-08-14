package Thread;

import java.awt.SystemTray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

import algorithm.Attribute;
import algorithm.Instance;
import computeValue.ComputeComposePos;
import computeValue.ComputeValue;

public class ThreadControl {

	// store solution and solution
	ArrayList<Instance> instanList;
	private int waitThread = 0;
	private int totalThread = 0;
	private boolean clearList;
	private double [] composeSolution;
	private Attribute AT;
	private ComputeComposePos computeComposePos;
	private int exchangeNow = 0;
	private int timeCount = 0;

	public ThreadControl(Attribute AT) {
		// TODO Auto-generated constructor stub
		instanList = new ArrayList<Instance>();
		this.AT = AT;
		totalThread = AT.getTotalThread();
		// an attribute of output bestValue
		clearList = false;
		composeSolution = new double[AT.getTotalThread()];
		computeComposePos = new ComputeComposePos(AT);

	}

	public synchronized void addPopulation(Instance instance) {

		// System.out.println(exchangeTime);
		// System.out.println(clearList);

		if (clearList) {

			clearList = false;
			instanList.clear();
		}
		instanList.add(instance);

		if (waitThread < totalThread - 1) {

			waitThread++;

			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			// sort arrayList <instance>
			sortArrayList();
			//print value
			printValue();
			
			notifyAll();
			waitThread = 0;

		}

	}
	
	public synchronized void composePos(double pos , int TN){		
		composeSolution[TN] = pos;			
	}

	public ArrayList<Instance> getInstance() {

		return instanList;

	}

	
	
	public void sortArrayList() {

		int leng = instanList.size();
		for (int i = 0; i < leng - 1; i++) {
			for (int j = 0; j < leng - i - 1; j++) {
				if (instanList.get(j).getBestValue() > instanList.get(j + 1).getBestValue()) {
					Collections.swap(instanList, j, j + 1);
				}
			}
		}		
		clearList = true;
		// System.out.println(instanList.size());

	}
	
	public void printValue(){
		
		exchangeNow++;
		//connection stable
		
//		if(instanList.get(0).getBestValue() < 0.01&& timeCount == 0){
//			
//			System.out.println(exchangeNow);
//			timeCount = 1;
//		}
		
		if (AT.getExchangeNum() > 1 && AT.getErrorRate() ==0&&exchangeNow == AT.getExchangeNum()) {
			
		
			System.out.println(instanList.get(0).getBestValue());
			
		}
		else if (AT.getErrorRate() > 0){
			computeComposePos.computeComposeValue(composeSolution);
			
		}
		
	}
	
	
	public void initialAttribute() {
		clearList = true;
		exchangeNow=0;
		timeCount = 0;
	}
	
	public synchronized void waitOtherThread() {

		if (waitThread < totalThread - 1) {

			waitThread++;

			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			notifyAll();

			waitThread = 0;
		}

	}

}
