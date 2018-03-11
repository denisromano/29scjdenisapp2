package br.com.fiap.a29scjdenisapp.adapter;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.fiap.a29scjdenisapp.R;
import br.com.fiap.a29scjdenisapp.api.ClickRecyclerView_Interface;
import br.com.fiap.a29scjdenisapp.model.Imovel;

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ImovelViewHolder>{

    public static ClickRecyclerView_Interface clickRecyclerViewInterface;
    Context mctx;
    private List<Imovel> imoveis;

    public ImovelAdapter(Context ctx, List<Imovel> listImoveis, ClickRecyclerView_Interface clickRecyclerViewInterface){
        this.mctx = ctx;
        this.imoveis = listImoveis;
        this.clickRecyclerViewInterface = clickRecyclerViewInterface;
    }

    @Override
    public ImovelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linha_lista_imoveis, viewGroup, false);
        return new ImovelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImovelViewHolder viewHolder, int i){
        Imovel imovel = imoveis.get(i);

        viewHolder.setIsRecyclable(false);

        viewHolder.tipoImovelView.setText(viewHolder.tipoImovelView.getText().toString()+": "+imovel.getTipo());
        viewHolder.localizaImovelView.setText(viewHolder.localizaImovelView.getText().toString()+": "+imovel.getLocalizacao());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount()
    {
        return imoveis.size();
    }


    protected class ImovelViewHolder extends RecyclerView.ViewHolder{
        protected TextView tipoImovelView,localizaImovelView,tvIDListaImovel;
        protected FloatingActionButton btnRemoverMaquinaListaMaquina;

        public ImovelViewHolder(final View itemView)        {
            super(itemView);

            tipoImovelView = (TextView) itemView.findViewById(R.id.tvTipoListaImovel);
            localizaImovelView = (TextView) itemView.findViewById(R.id.tvLocalizaListaImovel);
            btnRemoverMaquinaListaMaquina = (FloatingActionButton) itemView.findViewById(R.id.btnRemoverImovelListaImovel);

            //Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    clickRecyclerViewInterface.onCustomClick(imoveis.get(getLayoutPosition()));
                }
            });

            //Setup the click listener
            btnRemoverMaquinaListaMaquina.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    clickRecyclerViewInterface.onCloseButton(imoveis.get(getLayoutPosition()));
                }
            });
        }
    }

}
