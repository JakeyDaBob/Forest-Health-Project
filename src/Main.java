import window.MenuManager;
import window.MenuState;

import org.json.JSONObject;

public class Main
{
    public static void main(String[] args)
    {
        MenuManager.Innit();
        MenuManager.SetState(MenuState.Menu);

        JSONObject jobj = new JSONObject("{ \"abc\" : \"def\" }");
        System.out.println(jobj);
    }
}
