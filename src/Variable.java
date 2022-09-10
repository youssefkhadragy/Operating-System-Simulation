
public class Variable {
	private String stringData;
	private int intData;
	private String name;
	private Boolean flag;
	
	
	public String getStringData() {
		return stringData;
	}
	public void setStringData(String stringData) {
		this.stringData = stringData;
	}
	public int getIntData() {
		return intData;
	}
	public void setIntData(int intData) {
		this.intData = intData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Variable(String name,int intData){
		this.intData = intData;
		this.name = name;
		flag = true;
	}
	public Variable(String name,String stringData) {
		this.stringData = stringData;
		this.name = name;
		flag = false;
	}
	public String toString() {
		if(flag)
			return intData + " ";
		return stringData;
	}

	
}
