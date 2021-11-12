/**
 * @(#)complexNumber.java
 *
 *
 * @author
 * @version 1.00 2020/4/12
 */


public class complexNumber {
	private double realComponent;
	private double complexComponent;

    public complexNumber() {
    	realComponent = 1;
    	complexComponent = 1;
    }
    public complexNumber(double r, double i)
    {
    	realComponent = r;
    	complexComponent = i;
    }

    public complexNumber add(complexNumber num)
    {
    	complexNumber result=new complexNumber();
    	result.setRealComponent( realComponent + num.getRealComponent() );
		result.setComplexComponent( complexComponent + num.getComplexComponent() );
    	return result;
    }

    public complexNumber subtract(complexNumber num)
    {
    	complexNumber result=new complexNumber();
    	result.setRealComponent( realComponent - num.getRealComponent() );
		result.setComplexComponent( complexComponent - num.getComplexComponent() );
    	return result;
    }

    public complexNumber multiply(complexNumber num)
    {
    	complexNumber result=new complexNumber();
    	result.setRealComponent( realComponent*num.getRealComponent() - complexComponent*num.getComplexComponent() );
    	result.setComplexComponent( complexComponent*num.getRealComponent() + realComponent*num.getComplexComponent() );
    	return result;
    }

    public complexNumber addRealSubtractComplex(complexNumber num)
    {
    	complexNumber result=new complexNumber();
    	result.setRealComponent( realComponent + num.getRealComponent() );
		result.setComplexComponent( complexComponent - num.getComplexComponent() );
    	return result;
    }

    public double getRealComponent()
    {
    	return realComponent;
    }

    public double getComplexComponent()
    {
    	return complexComponent;
    }

    public void setRealComponent(double r)
    {
    	realComponent=r;
    }

    public void setComplexComponent(double i)
    {
    	complexComponent=i;
    }

	public String toString()
	{
		return realComponent+"+"+complexComponent+"i";
	}
}