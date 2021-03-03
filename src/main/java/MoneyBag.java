import java.util.Vector;
import java.util.Iterator;

class MoneyBag {
    private Vector<Money> bag;
    
    public MoneyBag(){
	this.bag = new Vector<Money>();
    }
    
    public Money add(Money m){
	Iterator<Money> it = this.bag.iterator();
	Money current;

	while(it.hasNext()){
	    current = it.next();
	    if(current.currency()==m.currency()){
		current.add(m);
		return current;
	    }
	}
	this.bag.add(m);
	return m;
    }

    public Money withdraw(Money m){
	Money mNeg = new Money(-m.amount(), m.currency());
	return this.add(mNeg);
    }    
}
