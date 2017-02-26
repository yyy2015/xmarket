package me.jcala.xmarket.mvp.user.login.register.phone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jcala.xmarket.R;
import me.jcala.xmarket.mvp.a_base.BaseActivity;
import me.jcala.xmarket.mvp.user.login.LoginRegisterActivity;

/**
 * Phone validation activity
 * @author cuihao
 */
public class RegisterPhoneActivity extends BaseActivity implements RegisterPhoneView{

    @BindView(R.id.register_phone_phone_autocomplete)
    EditText phone;

    private String userId="";

    private RegisterPhonePresenter presenter;

    private MaterialDialog progress;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.register_phone_layout);
        ButterKnife.bind(this);
        presenter = new RegisterPhonePresenterImpl(this,this);
        initDialog();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
    }

    @OnClick(R.id.register_phone_sub)
    public void phoneSub() {
        presenter.registerPhone(userId,phone.getText().toString().trim());
    }

    @Override
    public void whenFails(String msg) {
        new SuperToast(RegisterPhoneActivity.this)
                .setText(msg)
                .setDuration(Style.DURATION_LONG)
                .setColor(PaletteUtils.getTransparentColor(PaletteUtils.MATERIAL_RED))
                .setAnimations(Style.ANIMATIONS_FLY)
                .show();
    }

    @Override
    public void whenStartSetProgress() {
        progress.show();
    }

    @Override
    public void whenStopSetProgress() {
        progress.dismiss();
    }

    @Override
    public void whenRegisterSuccess() {
        Intent intent=new Intent(RegisterPhoneActivity.this,LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void initDialog() {
        progress = new MaterialDialog.Builder(this)
                .content(R.string.register_dialog_content)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .title(R.string.dialog_wait)
                .build();
    }
}
