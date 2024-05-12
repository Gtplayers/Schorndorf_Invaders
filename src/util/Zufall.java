package util;

/**
 * @author km
 */
public class Zufall
{
    /**
     * @return   Eine Zufallszahl von 1 bis 6 
     */
    public static int wuerfeln()
    {
        return  (int)(Math.random()*6 + 1);
    }

    /**
     * @return   Eine Zufallszahl von 1 bis 9 
     */
    public static int neun()
    {
        return  (int)(Math.random()*9 + 1);
    }

    /**
     * @return   Eine Zufallszahl von 1 bis 100 
     */
    public static int hundert()
    {
        return  (int)(Math.random()*100 + 1);
    }
    /**
     * @return   Eine Zufallszahl von 0 bis 255 
     */
    public static int zweihundertFuenfUndFuenfzig()
    {
        return  (int)(Math.random()*256);
    }
    /**
     * @return   Eine Zufallszahl von 0 bis 4 
     */
    public static int movement()
    {
        return  (int)(Math.random()*4);
    }
}
