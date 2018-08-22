package com.dx.springlearn.handlers.enums;

public enum ResultCodeEnums {
	 SUCCESS("SUCCESS", 1),
	 NOTDATA("NOTDATA", 2),
	 SERVER_ERROR("SERVER ERROR", 3),
	 RPC_ERROR("RPC ERROR", 100),
	 PARAM_ERROR("PARAM ERROR", 5);  
	
    private String name;  
    private int index;  
    private ResultCodeEnums(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    public static String getName(int index) {  
        for (ResultCodeEnums c : ResultCodeEnums.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    }  

}
