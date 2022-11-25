/**
* The "visitor" interface is an interface that has the necessary methods so that project,
* task and interval can use the different functionalities that they inherit from it.
* This interface implements the Visitor pattern, so that the project, the task and the
* interval can receive a visitor of this class and be able to call the methods they are
* interested in.
*/
public interface Visitor {
  void visitProject(Project project); /*This is an abstract method that is not implemented
  by the Visitor interface but by the classes that implement the class
  (Printer, SearchByName, SearchByTag). This method is the one used by the project to use
  the functionalities offered by the classes that implement the Visitor when the project
  receives a call by its "acceptVisitor" method.*/

  void visitTask(Task task); /*This is an abstract method that is not implemented by the
  Visitor interface but by the classes that implement the class
  (Printer, SearchByName, SearchByTag). This method is the one used by the task to use the
  functionalities offered by the classes that implement the Visitor when the task receives
  a call by its "acceptVisitor" method.*/

  void visitInterval(Interval interval); /*This is an abstract method that is not implemented
  by the Visitor interface but by the classes that implement the class
  (Printer, SearchByName, SearchByTag). This method is the one used by the interval to use the
  functionalities offered by the classes that implement the Visitor when the interval receives a
  call by its "acceptVisitor" method.*/
}
