package engine;

public class Text {
	private String text;
	private float x;
	private float y;
	private int life;
	private int age;
	private boolean infinite;
	private boolean doFade;
	
	public Text(String text, float x, float y) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.infinite = true;
	}

	public Text(String text, float x, float y, int life, boolean doFade) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.life = life;
		this.age = 0;
		this.infinite = false;
		this.doFade = doFade;
	}
	
	public void update(float delta) {
		age++;
	}
	
	public boolean isAlive() {
		return infinite || age < life;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}

	public boolean isDoFade() {
		return doFade;
	}

	public void setDoFade(boolean doFade) {
		this.doFade = doFade;
	}

}
