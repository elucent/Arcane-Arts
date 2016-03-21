package elucent.aa;

public class aaElementValue {
	public aaElement element;
	public double value;
	public aaElementValue(aaElement element, double value){
		this.value = value;
		this.element = element;
	}
	
	public double getValue(){
		return value;
	}
	
	public void setValue(double f){
		value = f;
	}
	
	public aaElement getElement(){
		return element;
	}
	
	public void setElement(aaElement e){
		element = e;
	}
}
