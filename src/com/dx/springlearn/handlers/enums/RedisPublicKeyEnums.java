package com.dx.springlearn.handlers.enums;

public enum RedisPublicKeyEnums {
	 LAST_BLOCK("last_block", 1),
	 REFRESH_LAST_BLOCK("refresh_block", 3),
	 BLOCK_("getblock_", 2),
	 TX_LIST("getTxList", 4),
	 PEER_LIST("getPeerList", 5),
	 BLOCK_COUNT("getblockcount",6),
	RAWMEM_POOL("getrawmempool",7);
    private String name;  
    private int index;  
    private RedisPublicKeyEnums(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    public static String getName(int index) {  
        for (RedisPublicKeyEnums c : RedisPublicKeyEnums.values()) {  
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
