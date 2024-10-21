package org.hbrs.se1.ws24.exercises.uebung3;



import java.util.List;

public class Client {

    public static void main(String[] args) {

        Container container = Container.getInstance();

       try {
           container.addMember(new MemberKonkret(1));
           container.addMember(new MemberKonkret(2));
           container.addMember(new MemberKonkret(3));
           container.addMember(new MemberKonkret(4));
       } catch (ContainerException e) {
           e.printStackTrace();
       }


       List<Member> liste = container.getCurrentList();

       MemberView view = new MemberView();

       view.dump(liste);





    }

}
