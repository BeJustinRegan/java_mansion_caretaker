
public class Robot extends CareTaker implements RechargeRobot{

	public Robot(int stamina) {
		super(stamina);
	}

	@Override
	public void recharge() {
		stamina++;
	}

}
