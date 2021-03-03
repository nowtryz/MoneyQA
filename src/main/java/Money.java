class Money {
    private double amount ;
    private String currency ;
    public Money( double amount , String currency ) {
	this.amount = amount ;
	this.currency = currency ;
    }
    public double amount() {
	return this.amount ;
    }
    public String currency() {
	return this.currency ;
    }
    public Money add (Money m) {
	return new Money( this.amount() + m.amount() , this.currency() ) ;
    }
}
