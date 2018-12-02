package com.jc.system.security.util;



public class License {
	
//	public static void verifyLicense(){
//		try{
//			License license = new License();
//			String path = license.getPath();
//			path = path.replace("%20", " ");
//			Verification verification = new Verification();
//			verification.startVer(path);
//		} catch(Exception e){
//			System.exit(0);
//		} catch(Throwable t){
//			System.exit(0);
//		}
//	}

	public String getPath(){
		return this.getClass().getClassLoader().getResource("/").getPath();
		
	}
	
}
