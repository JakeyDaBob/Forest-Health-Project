import application.AppInfo;
import application.AppMode;
import window.MenuManager;
import window.MenuState;

public class Main
{
    public static void main(String[] args)
    {
        if (args.length > 0)
        {
            switch (args[0])
            {
                case "-s":
                    AppInfo.Mode = AppMode.Scientist;
                    break;

                case "-c":
                    AppInfo.Mode = AppMode.Citizen;
                    break;

                default:
                    System.err.println("Unhandled run flag: '" + args[0] + "'");
                    break;
            }
        }

        MenuManager.Innit(MenuState.Splash);
    }
}
