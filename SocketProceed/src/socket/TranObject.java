package socket;

import java.io.Serializable;

public class TranObject<T> implements Serializable {
	private TranObjectType type;
	private int user;
	public TranObjectType getType() {
		return type;
	}
	public void setType(TranObjectType type){
		this.type=type;
	}
	public int getUser(){
		return user;
	}
	public void setUser(int user){
		this.user=user;
	}
}
