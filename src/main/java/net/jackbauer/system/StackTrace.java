package net.jackbauer.system;

public class StackTrace {
	public static void main(String[] args) {
        new StackTrace().callMethod();
    }
    
    void callMethod(){
        calledMethod();
    }
    
    void calledMethod(){
         StackTraceElement[] a = new Throwable().getStackTrace();
         
         for(int i = a.length - 1; i > 0 ; i--){
             System.out.print("클래스 - " + a[i].getClassName());
             System.out.print(", 메소드 - "+a[i].getMethodName());
             System.out.print(", 라인 - "+a[i].getLineNumber());
             System.out.print(", 파일 - "+a[i].getFileName());
             System.out.println();
         }
    }
}
