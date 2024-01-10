import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Body extends JComponent {
    int [][] playingField = new int[3][3]; //массив состояний игровых ячеек
    public static final int fieldEmpty = 0;//значение пустой игровой клетки
    public static final int field_X = 10;//значение клетки Х
    public static final int field_0 = 200;//щначение клетки 0
    boolean queue;

    public Body(){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    @Override
    public void paint (Graphics g){
        //Отрисовка игрового поля
        super.paint(g);
        g.clearRect(0,0, getWidth(), getHeight());
        markup(g);
        drawX0(g);
    }

    protected void processMouseEvent(MouseEvent mouseEvent){
        super.processMouseEvent(mouseEvent);
        if(mouseEvent.getButton() == MouseEvent.BUTTON1) { //Проверяем кликнули левой кнопкой
            int x = mouseEvent.getX(); //Координаты клика
            int y = mouseEvent.getY();
            int a = (int) ((float) x / getWidth() * 3);
            int b = (int) ((float) y / getHeight() * 3);

            if (playingField[a][b] == fieldEmpty) { //Проверка очередности хода
                playingField[a][b] = queue ? field_X : field_0;
                queue = !queue; //меняется очередность
                repaint(); //перерисовывается картинка

                int res = check();
                if(res != 0){
                    if(res == field_0 * 3) {
                        JOptionPane.showMessageDialog(this, "Нолики победили", "Победа", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (res == field_X * 3){
                        JOptionPane.showMessageDialog(this, "Крестики победили", "Победа", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Победила дружба", "Ничья", JOptionPane.INFORMATION_MESSAGE);
                    }
                    initGame();
                    repaint();
                }
            }
        }

    }
    public void markup(Graphics line) {
        //разметка игрового на ячейки
        int h = getHeight();
        int w = getWidth();
        int ch = h / 3;
        int cw = w / 3;
        line.setColor(Color.GREEN);
        for(int i = 1; i < 3; i ++) {
            line.drawLine(0, ch * i, w, ch * i);
            line.drawLine(cw * i, 0, cw * i, h);
        }
    }

    public void initGame() {
        //очистка игрового поля
    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
            playingField[i][j] = fieldEmpty; //обнуляем все ячейки
        }
    }
    queue = true;
    }

    void drawX(int a, int b, Graphics g){
        g.setColor(Color.BLUE);
        int h = getHeight() / 3;
        int w = getWidth() / 3;
        int x = a * w;
        int y = b * h;
        g.drawLine(x, y, x + w, y + h); //Рисуем крестик
        g.drawLine(x, y + h, x + w, y);
    }

    void draw0(int a, int b, Graphics g){
        g.setColor(Color.BLUE);
        int h = getHeight() / 3;
        int w = getWidth() / 3;
        int x = a * w;
        int y = b * h;
        g.drawOval(x + 5 * w / 100, y, w * 9 / 10, h); //делаем нолик вытянутым и чуть меньше клетки
    }

    void drawX0(Graphics g){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(playingField[i][j] == field_X){
                    drawX(i, j, g);
                } else if(playingField[i][j] == field_0){
                    draw0(i, j, g);
                }
            }
        }
    }

    int check(){
        //проверяем суммы по диагоналям для определения победителя
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i < 3; i++){
            diag += playingField[i][i];
            diag2 += playingField[i][2 - i];
        }
        //Проверяем кто выйграл по диагоналям
        if(diag == field_X * 3 || diag == field_0 * 3) return diag;
        if(diag2 == field_X * 3 || diag2 == field_0 * 3) return diag2;
        int check_i;
        int check_j;
        boolean endGame = false;
        //Проверяем суммы рядов
        for(int i = 0; i < 3; i++){
            check_i = 0;
            check_j = 0;
            for(int j = 0; j < 3; j++) {
                if (playingField[i][j] == 0) endGame = true;
                check_i += playingField[i][j];
                check_j += playingField[j][i];

            }
            //проверяем кто выйграл по рядам
            if(check_i == field_0 * 3 || check_i == field_X * 3) return check_i;
            if(check_j == field_0 * 3 || check_j == field_X * 3) return check_j;
        }
        if(endGame) return 0;
        else return -1;
    }
}

