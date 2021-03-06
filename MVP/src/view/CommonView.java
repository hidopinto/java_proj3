package view;

import java.util.Observable;

/**
 * CommonView defines all the qualities basic a class which extends View should have.
 * @author Yehuda Hido Pinto
 * @see View
 * @see Observable
 */
public abstract class CommonView extends Observable implements View{

	/**
	 * just like Observable.setChanged, but public.
	 */
	public void setChanged1 (){}
	
}
