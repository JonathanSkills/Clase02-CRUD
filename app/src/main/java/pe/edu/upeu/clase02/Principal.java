package pe.edu.upeu.clase02;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import adapter.LoadData;
import dao.ProductoDaoImp;
import dto.Producto;
import interfaces.ILoadMore;

public class Principal extends AppCompatActivity {

    private ProductoDaoImp pd = new ProductoDaoImp();
    List<Producto> productos = pd.getLista();
    LoadData loadData;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        final RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadData = new LoadData(recycler,this,productos);


        /*for (int x=1;x<=10;x++){
            Producto p = new Producto();
            p.setNombre("producto"+x);
            p.setPrecio(Double.parseDouble(String.valueOf(x)));
            productos.add(p);
        }*/

        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Eliminado correctamente! "+recycler.getChildAdapterPosition(view),Toast.LENGTH_SHORT).show();
                final TextView hidden = (TextView) findViewById(R.id.hidden_value);
                if(productos.size()>=1){

                    final ImageButton btneliminar = (ImageButton) findViewById(R.id.btneliminar);
                    btneliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //pd.delete(Integer.parseInt(hidden.getText().toString()));
                            Toast.makeText(Principal.this,"Eliminado correctamente! "+recycler.getChildAdapterPosition(view),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        recycler.setAdapter(loadData);
        final FloatingActionButton mShowDialog=(FloatingActionButton) findViewById(R.id.btnShowAdd);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Principal.this);
                final View mView = getLayoutInflater().inflate(R.layout.insertar_producto,null);
                final TextView tv = (TextView) mView.findViewById(R.id.tvnew);
                final EditText txtcod = (EditText) mView.findViewById(R.id.txtcod);
                final EditText txtprod = (EditText) mView.findViewById(R.id.txtprod);
                final EditText txtprecio = (EditText) mView.findViewById(R.id.txtprecio);
                final EditText txtcant = (EditText) mView.findViewById(R.id.txtcant);

                final Button btnguardar = (Button) mView.findViewById(R.id.btnguardar);

                String codigo = "";
                int tamanio = loadData.getItemCount();
                if(tamanio>=0&&tamanio<10){
                    codigo = "PROD-000"+(tamanio+1);
                }else if(tamanio>=10&&tamanio<100){
                    codigo = "PROD-00"+(tamanio+1);
                }else if(tamanio>=100&&tamanio<1000){
                    codigo = "PROD-0"+(tamanio+1);
                }else {
                    codigo = "PROD-"+(tamanio+1);
                }
                txtcod.setText(codigo);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                btnguardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cod = txtcod.getText().toString();
                        String prod = txtprod.getText().toString();
                        double precio = Double.parseDouble(txtprecio.getText().toString());
                        int cant = Integer.parseInt(txtcant.getText().toString());
                        Producto p = new Producto(cod,prod,precio,cant);
                        pd.agregar(p);
                        loadData.notifyDataSetChanged();
                        dialog.hide();

                    }
                });

                Typeface font = Typeface.createFromAsset(getAssets(),"fonts/Rochester-Regular.ttf");
                tv.setTypeface(font);


            }
        });

        loadData.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {


                //Log.d("dsd","Este es el tamanio"+productos.size());
                /*if(productos.size()<50){*/
                    productos.add(null);
                    loadData.notifyItemInserted(productos.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            productos.remove(productos.size()-1);
                            loadData.notifyItemRemoved(productos.size());

                            /*int index = productos.size();
                            int end=index+10;
                            for(int i=index;i<end;i++){
                                String nombre = UUID.randomUUID().toString();
                                Producto p = new Producto();
                                p.setNombre(nombre);
                                p.setPrecio(6.5);
                                productos.add(p);
                            }*/

                            loadData.notifyDataSetChanged();
                            loadData.setLoaded();
                        }
                    },2000);
                /*}else{
                    Toast.makeText(Principal.this,"Carga de datos completa!",Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }
}
