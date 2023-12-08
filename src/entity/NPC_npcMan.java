package entity;

import Main.GamePanel;
import java.util.Random;

public class NPC_npcMan  extends Entity{
    public NPC_npcMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
    }

    //  Метод загружает спрайты анимации персонажа в 4 направлениях: вверх, вниз, вправо, влево
    public void getImage() {

        up1 = setup("/npc/npcMan_up_1");
        up2 = setup("/npc/npcMan_up_2");
        up3 = setup("/npc/npcMan_up_3");
        up4 = setup("/npc/npcMan_up_4");
        up5 = setup("/npc/npcMan_up_5");
        up6 = setup("/npc/npcMan_up_6");

        down1 = setup("/npc/npcMan_down_1");
        down2 = setup("/npc/npcMan_down_2");
        down3 = setup("/npc/npcMan_down_3");
        down4 = setup("/npc/npcMan_down_4");
        down5 = setup("/npc/npcMan_down_5");
        down6 = setup("/npc/npcMan_down_6");

        right1 = setup("/npc/npcMan_right_1");
        right2 = setup("/npc/npcMan_right_2");
        right3 = setup("/npc/npcMan_right_3");
        right4 = setup("/npc/npcMan_right_4");
        right5 = setup("/npc/npcMan_right_5");
        right6 = setup("/npc/npcMan_right_6");

        left1 = setup("/npc/npcMan_left_1");
        left2 = setup("/npc/npcMan_left_2");
        left3 = setup("/npc/npcMan_left_3");
        left4 = setup("/npc/npcMan_left_4");
        left5 = setup("/npc/npcMan_left_5");
        left6 = setup("/npc/npcMan_left_6");
    }

    public void setAction() {

        actionLockCounter ++;

        if(actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100)+1; // выборка числа от 1 до 100

            if(i <=25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "right";
            }
            if(i > 75) {
                direction = "left";
            }

            actionLockCounter = 0;
        }
    }
}
