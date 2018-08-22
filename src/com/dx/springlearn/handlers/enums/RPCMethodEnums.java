package com.dx.springlearn.handlers.enums;

public enum RPCMethodEnums {
	 GET_INFO("getinfo", 1),
	 GET_BLOCK("getblock", 2),
	 GET_BLOCK_HASH("getblockhash", 3),
	 GET_TRANSACTION_TID("gettransaction", 5),
	 GET_ACCOUNT_ADDR("getaccountaddress", 6),
	 GET_RAWMEMPOOL("getrawmempool", 7),
	 GET_TX_LISTS("listtransactions",8),
	 GET_BLOCK_COUNT("getblockcount", 4),
	GET_PEER_INFO("getpeerinfo",9),
	GET_MINING_INFO("getmininginfo",10);
    private String name;  
    private int index;  
    private RPCMethodEnums(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    public static String getName(int index) {  
        for (RPCMethodEnums c : RPCMethodEnums.values()) {  
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
