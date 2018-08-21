package adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import dto.Producto;
import interfaces.ILoadMore;
import pe.edu.upeu.clase02.R;

class  LoadingViewHolder extends RecyclerView.ViewHolder
{

    public ProgressBar progressBar;
    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder{
    public TextView cod,name,precio,cant,hidden;


    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        cod=itemView.findViewById(R.id.txtCod2);
        name=itemView.findViewById(R.id.txtNombre2);
        precio=itemView.findViewById(R.id.txtPrecio2);
        cant=itemView.findViewById(R.id.txtCant2);
        hidden=itemView.findViewById(R.id.hidden_value);
    }
}
public class LoadData extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    private View.OnClickListener listener;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Producto> productos;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;

    public LoadData(RecyclerView recyclerView,Activity activity, List<Producto> productos) {
        this.activity = activity;
        this.productos = productos;

        final LinearLayoutManager linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount =linearLayoutManager.getItemCount();
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading&&totalItemCount<=(lastVisibleItem+visibleThreshold)){
                    if(loadMore!=null)
                        loadMore.onLoadMore();
                    isLoading=true;
                }

            }
        });
    }

    //Ctrl+O


    @Override
    public int getItemViewType(int position) {
        return productos.get(position)==null? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType==VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity).
                    inflate(R.layout.activity_listar_producto,viewGroup,false);
            return new ItemViewHolder(view);
        }else if(viewType==VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).
                    inflate(R.layout.loading,viewGroup,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if(holder instanceof ItemViewHolder){
            Producto p = productos.get(i);
            ItemViewHolder viewHolder=(ItemViewHolder) holder;
            viewHolder.cod.setText(productos.get(i).getCod());
            viewHolder.name.setText(productos.get(i).getNombre());
            viewHolder.precio.setText(String.valueOf(productos.get(i).getPrecio()));
            viewHolder.cant.setText(String.valueOf(productos.get(i).getCant()));
            viewHolder.hidden.setText(String.valueOf(i));
        }
        else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }


}
