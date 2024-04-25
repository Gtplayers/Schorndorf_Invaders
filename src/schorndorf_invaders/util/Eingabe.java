package util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author km
 */

public class Eingabe
{
	/**
	 * @param   text    muss den Text enthalten, der an der Konsole als Eingabeaufforderung angezeigt werden soll
	 * @return  die an der Konsole eingegebene Ganzzahl mit dem Wertebereich -128 ... 127
	 */    
	public static byte readByte(String text)
	{
		System.out.println(text);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		byte bZahl = 0;
		try
		{
			bZahl= Byte.parseByte(br.readLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Eingabefehler: Geben Sie bitte eine Ganzzahl im Bereich {-128 ... 127} ein!");
			bZahl = Byte.MIN_VALUE;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return bZahl;
	}

	/**
	 * @param   text    muss den Text enthalten, der an der Konsole als Eingabeaufforderung angezeigt werden soll
	 * @return  die an der Konsole eingegebene Ganzzahl mit dem Wertebereich -32768 ... 32767
	 */    
	public static short readShort(String text)
	{
		System.out.println(text);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		short sZahl = 0;
		try
		{
			sZahl= Short.parseShort(br.readLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Eingabefehler: Geben Sie bitte eine Ganzzahl im Bereich {-32768 ... 32767} ein!");
			sZahl = Short.MIN_VALUE;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sZahl;
	}

	/**
	 * @param   text    muss den Text enthalten, der an der Konsole als Eingabeaufforderung angezeigt werden soll
	 * @return  die an der Konsole eingegebene Ganzzahl -2147483648 ... 2147483647
	 */    
	public static int readInt(String text)
	{
		System.out.println(text);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int iZahl = 0;
		try
		{
			iZahl= Integer.parseInt(br.readLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Eingabefehler: Geben Sie bitte eine Ganzzahl im Bereich {-2147483648 ... 2147483647} ein!");
			iZahl = Integer.MIN_VALUE;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return iZahl;
	}

	/**
	 * @param   text    muss den Text enthalten, der an der Konsole als Eingabeaufforderung angezeigt werden soll
	 * @return  die an der Konsole eingegebene Ganzzahl -92233772036854775808 ... 92233772036854775807
	 */    
	public static long readLong(String text)
	{
		System.out.println(text);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long lZahl = 0;
		try
		{
			lZahl= Long.parseLong(br.readLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Eingabefehler: Geben Sie bitte eine Ganzzahl im Bereich {-92233772036854775808 ... 92233772036854775807} ein!");
			lZahl = Long.MIN_VALUE;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return lZahl;
	}

	/**
	 * @param   text    muss den Text enthalten, der an der Konsole als Eingabeaufforderung angezeigt werden soll
	 * @return  die an der Konsole eingegebene Dezimalzahl 
	 */    
	public static double readDouble(String text)
	{
		System.out.println(text);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		double dZahl = 0;
		try
		{
			dZahl= Float.parseFloat(br.readLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Eingabefehler: Geben Sie bitte eine Dezimalzahl ein!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return dZahl;
	}

	/**
	 * @param   text    muss den Text enthalten, der an der Konsole als Eingabeaufforderung angezeigt werden soll
	 * @return  den an der Konsole eingegebene Text
	 */    
	public static String readString(String text)
	{
		System.out.println(text);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String sText = "";
		try
		{
			sText= br.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sText;
	}

	/**
	 * @param   text    muss den Text enthalten, der an der Konsole als Eingabeaufforderung angezeigt werden soll
	 * @return  das an der Konsole eingegebene Zeichen
	 */    
	public static char readChar(String text)
	{
		System.out.println(text);
		char zeichen ='0';
		try
		{
			zeichen = (char) System.in.read();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		flushInputBuffer();
		return zeichen;
	}

	/*
	 * PROBLEM
	 * Bei der Benutzereingabe 'A' + [Enter]
	 * enstehen in <System.in> die ASCII Codes: 65|13|10
	 *    65 -> 'A'
	 *    13 -> newLine
	 *    10 -> carriageReturn
	 * <System.in.read()> liest nur das 1te Byte, der Rest bleibt 
	 * stehen und verursacht beim naechsten Aufruf Probleme
	 * 
	 * ABHILFE
	 * bis einschliesslich letztes Zeichen lesen
	 */
	private static void flushInputBuffer()
	{
		int tmp = 0;
		do
		{
			try
			{
				tmp = System.in.read();
			}
			catch (java.io.IOException e)
			{
				e.printStackTrace();
			}
		} while (tmp != 10);
	}
}
