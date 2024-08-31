import window.MenuManager;
import window.MenuState;

public class Main
{
    public static void main(String[] args)
    {
        MenuManager.Innit();
        MenuManager.SetState(MenuState.Menu);
    }
}
