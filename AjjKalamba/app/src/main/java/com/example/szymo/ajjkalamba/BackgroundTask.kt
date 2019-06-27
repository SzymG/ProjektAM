package com.example.szymo.ajjkalamba

import android.content.Context
import android.os.AsyncTask
import android.widget.ArrayAdapter
import android.widget.Toast

public class BackgroundTask(
    ctx: Context,
    adapter: ArrayAdapter<String>,
    list: ArrayList<String>
): AsyncTask<String, String, String>() {

    val db = WordsDataBase(ctx)
    val con = ctx
    val adp = adapter
    val lst = list
    var str = ""

    override fun doInBackground(vararg params: String): String {
        val status = db.insertData(params[0], params[1], params[2])
        str = params[1]+": "+params[2]
        if(status != ((-1).toLong())){
            return "OK"
        }
        return "NO"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            if(result.compareTo("OK")==0) {
                lst.add(str)
                adp.notifyDataSetChanged()
                Toast.makeText(con, "Pomyślnie dodano do bazy danych", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(con,"Niepowodzenie przy dodawaniu do bazy danych", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*val status = db.insertData(login, editKategoria.text.toString(), editHaslo.text.toString())
                    */
    /*list.add(editKategoria.text.toString()+": "+editHaslo.text.toString())
    adapter.notifyDataSetChanged()
    Toast.makeText(applicationContext,"Pomyślnie dodano do bazy danych", Toast.LENGTH_SHORT).show()
}*/

}