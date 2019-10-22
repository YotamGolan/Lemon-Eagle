
//-----------------------------------------------------------------------------
//Yotam Golan, ID:yogolan
//HelloUser is the sole file in this project, other then HelloUser2.
//It prints Hello and the BASH username, followed by numerous system information
//File ran by using specified Makefile and then running the created .jar
//-----------------------------------------------------------------------------
class HelloUser{
   public static void main( String[] args ){
	   //Assigns string names to various system properties
      String userName = System.getProperty("user.name");
      String os       = System.getProperty("os.name");
      String osVer    = System.getProperty("os.version");
      String jre      = System.getProperty("java.runtime.name");
      String jreVer   = System.getProperty("java.runtime.version");
      String jvm      = System.getProperty("java.vm.name");
      String jvmVer   = System.getProperty("java.vm.version");
      String javaHome = System.getProperty("java.home");
      long freemem    = Runtime.getRuntime().freeMemory();
      long time       = System.currentTimeMillis();
	    //Prints out all relevant information
      System.out.println("Hello "+userName);
      System.out.println("Operating system: "+os+" "+osVer);
      System.out.println("Runtime environment: "+jre+" "+jreVer);
      System.out.println("Virtual machine: "+jvm+" "+jvmVer);
      System.out.println("Java home directory: "+javaHome);
      System.out.println("Free memory: "+freemem+" bytes");
      System.out.printf("Time: %tc.%n", time);
   }
}
