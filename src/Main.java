import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("MyGame1"); //Название окна
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Крестик закрытия окна
        window.setSize(300, 300);//Размер окна
        window.setResizable(false); //Фиксируем размер окна
        window.add(new Body()); //Создаю объект и добавляю его отрисовку
        window.setVisible(true); //Видимость окна
    }
}