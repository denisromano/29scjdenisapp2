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
import br.com.fiap.a29scjdenisapp.model.Login;

public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.LoginViewHolder>{

    public static ClickRecyclerView_Interface clickRecyclerViewInterface;
    Context mctx;
    private List<Login> logins;

    public LoginAdapter(Context ctx, List<Login> listLogin, ClickRecyclerView_Interface clickRecyclerViewInterface){
        this.mctx = ctx;
        this.logins = listLogin;
        this.clickRecyclerViewInterface = clickRecyclerViewInterface;
    }

    @Override
    public LoginViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linha_lista_login, viewGroup, false);
        return new LoginViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoginViewHolder viewHolder, int i){
        Login user = logins.get(i);

        viewHolder.setIsRecyclable(false);

        viewHolder.loginView.setText(viewHolder.loginView.getText().toString()+": "+user.getLogin());
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
        return logins.size();
    }

    protected class LoginViewHolder extends RecyclerView.ViewHolder{
        protected TextView loginView,nivelAdmView;

        protected FloatingActionButton btnRemoverUserListaLogin;

        public LoginViewHolder(final View itemView){
            super(itemView);

            loginView = (TextView) itemView.findViewById(R.id.tvNomeListaLogin);
            btnRemoverUserListaLogin = (FloatingActionButton) itemView.findViewById(R.id.btnRemoverUserListaLogin);

            //Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    clickRecyclerViewInterface.onCustomClick(logins.get(getLayoutPosition()));
                }
            });

            //Setup the click listener
            btnRemoverUserListaLogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    clickRecyclerViewInterface.onCloseButton(logins.get(getLayoutPosition()));
                }
            });
        }
    }


}
