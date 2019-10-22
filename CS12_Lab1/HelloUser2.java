//-----------------------------------------------------
// Yotam Golan, ID:yogolan
//HelloUser2 is the sole file in this project, other then HelloUser.
//It prints hello and then the BASH username
//File ran by using specified Makefile and then running the created .jar
//-----------------------------------------------------
class HelloUser2 {
    static public void main(String[] args) {
		//prints out Hello and BASH username
        System.out.println("Hello" +System.getProperty("user.name"));
    }
}
