package wine.searcher;

import java.util.ArrayList;
import java.util.HashMap;

import com.sentaca.android.accordion.widget.AccordionView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WineSearcherActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button search = (Button)findViewById(R.id.button1);
        final EditText nomVin= (EditText)findViewById(R.id.editText1);
        final EditText annee = (EditText)findViewById(R.id.editText2);
        final Context context = this;
        
        final AccordionView v = (AccordionView) findViewById(R.id.accordion_view);
        
        search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Request requete = new Request();
				requete.WineYear=annee.getText().toString();
				requete.WineName=nomVin.getText().toString();
				requete.Location="France";
			
				try {
					requete.getWinesInformations();
					
					//R�cup�ration de la listview cr��e dans le fichier main.xml
					ListView maListViewPerso = (ListView) findViewById(R.id.listviewperso);
			        
					//Cr�ation de la ArrayList qui nous permettra de remplir la listView
			        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		        
					for (int i =0; i< requete.Response.wineNameList.size(); i++)
					{
						WineName CurrentWine = requete.Response.wineNameList.get(i);

				        //On d�clare la HashMap qui contiendra les informations pour un item
				        HashMap<String, String> map;
				 
				        //Cr�ation d'une HashMap pour ins�rer les informations du premier item de notre listView
				        map = new HashMap<String, String>();
				        map.put("nom", CurrentWine.Name);
				        map.put("prix", String.valueOf(CurrentWine.PriceAverage));
				        map.put("ann�e", String.valueOf(requete.WineYear));
				        
				        //on ins�re la r�f�rence � l'image (converti en String car normalement c'est un int) que l'on r�cup�rera dans l'imageView cr�� dans le fichier affichageitem.xml
				        map.put("img", "");
				        //enfin on ajoute cette hashMap dans la arrayList
				        listItem.add(map);

					}
					
			        
			        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sents dans notre list (listItem) dans la vue affichageitem
			        SimpleAdapter mSchedule = new SimpleAdapter (WineSearcherActivity.this.getBaseContext(), listItem, R.layout.affichageitem,
			               new String[] {"img", "nom", "prix", "ann�e"}, new int[] {R.id.text1, R.id.editText1, R.id.prix, R.id.editText2});
			 
			        //On attribue � notre listView l'adapter que l'on vient de cr�er
			        maListViewPerso.setAdapter(mSchedule);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
    }
}