package me.jcala.xmarket.mvp.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.realm.Realm;
import io.realm.RealmResults;
import me.jcala.xmarket.AppConf;
import me.jcala.xmarket.R;
import me.jcala.xmarket.app.App;
import me.jcala.xmarket.data.pojo.Message;
import me.jcala.xmarket.data.pojo.RealmTrade;
import me.jcala.xmarket.data.pojo.Team;
import me.jcala.xmarket.data.pojo.User;
import me.jcala.xmarket.data.storage.UserIntermediate;
import me.jcala.xmarket.mvp.message.MessageFragment;
import me.jcala.xmarket.mvp.school.SchoolFragment;
import me.jcala.xmarket.mvp.sort.TradeTagFragment;
import me.jcala.xmarket.mvp.team.TeamFragment;
import me.jcala.xmarket.mvp.user.login.LoginRegisterActivity;
import me.jcala.xmarket.mvp.user.team.UserTeamActivity;
import me.jcala.xmarket.mvp.user.trade.UserTradeActivity;
import me.jcala.xmarket.util.JpushUtils;

public class MainPresenterImpl implements MainPresenter {

    //added by yyy
    private boolean doShowSearch;
    //end added

    private AppCompatActivity context;
    private TeamFragment teamFragment;
    private TradeTagFragment tradeTagFragment;
    private SchoolFragment schoolFragment;
    private MessageFragment messageFragment;
    private FragmentManager fm;
    private MaterialSearchView mSearchView;
//    private MenuItem searchMenuItem;
    TextView toolbarTitle;
    BottomNavigationBar mBottomNavigationBar;
    private Realm realmDefault;
    private static String TAG = "MainPresenter";
    public MainPresenterImpl(AppCompatActivity context,Realm realm) {
        this.context = context;
        this.realmDefault=realm;
        fm = context.getFragmentManager();
        toolbarTitle=(TextView)context.findViewById(R.id.toolbar_title);
        mBottomNavigationBar=(BottomNavigationBar)context.findViewById(R.id.bottom_navigation_bar);

        doShowSearch = true;

        mSearchView = (MaterialSearchView)context.findViewById(R.id.search_view);
    }

    @Override
    public boolean init(MaterialSearchView searchView, View header) {
        boolean result = initHeader(header);
        initSearchView(searchView);
        initBottomMenu();
        return result;
    }

    public void initSearchView(MaterialSearchView searchView) {
        searchView.setVoiceSearch(false);
//        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setSuggestions(context.getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //dbc add
                schoolFragment.searchTrades(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    public boolean initHeader(View headerLayout) {
        User user= UserIntermediate.instance.getUser(context);
        if (user!=null && user.getUsername()!=null && !user.getPhone().isEmpty()) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, user.getId()));
        } else return false;
        TextView username=(TextView) headerLayout.findViewById(R.id.info_username);
        TextView phone=(TextView) headerLayout.findViewById(R.id.info_phone);
        SimpleDraweeView avatar=(SimpleDraweeView) headerLayout.findViewById(R.id.info_avatar);
        username.setText(user.getUsername());
        phone.setText(user.getPhone());
        avatar.setImageURI(Uri.parse(AppConf.BASE_URL+user.getAvatarUrl()));
        return true;
    }

    public void initBottomMenu() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.mipmap.menu_school, "本校").setActiveColorResource(R.color.black))
                .addItem(new BottomNavigationItem(R.mipmap.menu_sort, "分类").setActiveColorResource(R.color.black))
                .addItem(new BottomNavigationItem(R.mipmap.menu_team, "志愿队").setActiveColorResource(R.color.black))
                .addItem(new BottomNavigationItem(R.mipmap.menu_message, "消息").setActiveColorResource(R.color.black))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        toolbarTitle.setText(R.string.MainActivity_title_school);
                        //dbc add: disable search
//                        searchMenuItem.setEnabled(true);
//                        searchMenuItem.setVisible(true);
                        //dbc add end
                        showFragment(0);
                        break;
                    case 1:
                        toolbarTitle.setText(R.string.MainActivity_title_sort);
                        //dbc add: disable search
//                        searchMenuItem.setEnabled(false);
//                        searchMenuItem.setVisible(false);
                        //dbc add end
                        showFragment(1);
                        break;
                    case 2:
                        toolbarTitle.setText(R.string.MainActivity_title_team);
                        //dbc add: disable search
//                        searchMenuItem.setEnabled(false);
//                        searchMenuItem.setVisible(false);
                        //dbc add end
                       showFragment(2);
                        break;
                    case 3:
                        toolbarTitle.setText(R.string.MainActivity_title_message);
                        //dbc add: disable search
//                        searchMenuItem.setEnabled(false);
//                        searchMenuItem.setVisible(false);
                        //dbc add end
                        showFragment(3);
                        break;
                }

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        toolbarTitle.setText(R.string.MainActivity_title_school);
        showFragment(0);
    }

    //added by yyy
    @Override
    public boolean showSearch(){
        return doShowSearch;
    }
    //end added

    public void showFragment(int position) {
        FragmentTransaction ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0 :
                doShowSearch = true;//added
                if (schoolFragment != null) {
                    ft.show(schoolFragment);
                } else {
                    schoolFragment = new SchoolFragment();
                    ft.add(R.id.frame_layout, schoolFragment);
                }
                break;
            case 1 :
                doShowSearch=false;//added
                if (tradeTagFragment != null) {
                    ft.show(tradeTagFragment);
                } else {
                    tradeTagFragment = new TradeTagFragment();
                    ft.add(R.id.frame_layout, tradeTagFragment);
                }
                break;
            case 2 :
                doShowSearch=false;//added
                if (teamFragment != null) {
                    ft.show(teamFragment);
                } else {
                    teamFragment = new TeamFragment();
                    ft.add(R.id.frame_layout, teamFragment);
                }
                break;
            case 3 :
                doShowSearch=false;//added
                if (messageFragment != null) {
                    ft.show(messageFragment);
                } else {
                    messageFragment = new MessageFragment();
                    ft.add(R.id.frame_layout, messageFragment);
                }
                break;
        }
        //added by yyy
        context.invalidateOptionsMenu();//重新渲染菜单
        if(doShowSearch){
            mSearchView.setVisibility(View.VISIBLE);
        }else{
            mSearchView.setVisibility(View.GONE);
        }
        //end added
        ft.commit();
    }

    public void hideAllFragment(FragmentTransaction ft) {
        if (schoolFragment != null) {
            ft.hide(schoolFragment);
        }
        if (tradeTagFragment != null) {
            ft.hide(tradeTagFragment);
        }
        if (teamFragment != null) {
            ft.hide(teamFragment);
        }
        if (messageFragment != null) {
            ft.hide(messageFragment);
        }
    }


//    public void setSearchMenuItem(MenuItem item) {
//        this.searchMenuItem = item;
//    }

    @Override
    public void slideJump(int id) {
        int type=-1;
        switch (id){
            case R.id.info_team:
                Intent teamIntent=new Intent(context, UserTeamActivity.class);
                context.startActivity(teamIntent);
                break;

            case R.id.info_logout:
                executeLogout();
                break;
            case R.id.info_author:
                Uri uri=Uri.parse("https://github.com/jcalaz");
                Intent uriIntent=new Intent(Intent.ACTION_VIEW,uri);
                context.startActivity(uriIntent);
                break;
            case R.id.info_uncomplete:
                type=0;
                break;

            case R.id.info_sell:
                type=1;
                break;

            case R.id.info_bought:
                type=2;
                break;

            case R.id.info_sold:
                type=3;
                break;

            case R.id.info_donation:
                type=4;
                break;
            default:break;
        }
        if (type<0){
            return;
        }
        Intent tradeIntent=new Intent(context, UserTradeActivity.class);
        tradeIntent.putExtra("type",type);
        context.startActivity(tradeIntent);
    }
    private void executeLogout(){
        UserIntermediate.instance.logOut(context);

        final RealmResults<RealmTrade> results = realmDefault.where(RealmTrade.class).findAll();//清除school存储
        realmDefault.executeTransaction((Realm realm) -> results.deleteAllFromRealm());

        final RealmResults<Team> teamResults = realmDefault.where(Team.class).findAll();//清除志愿队存储
        realmDefault.executeTransaction((Realm realm) -> teamResults.deleteAllFromRealm());

        final RealmResults<Message> msgResults = realmDefault.where(Message.class).findAll();//清除消息存储
        realmDefault.executeTransaction((Realm realm) -> msgResults.deleteAllFromRealm());

        Intent loginIntent=new Intent(context, LoginRegisterActivity.class);
        context.startActivity(loginIntent);
        context.finish();
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
//                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (JpushUtils.isConnected(App.getInstance().getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            JpushUtils.showToast(logs, App.getInstance().getApplicationContext());
        }

    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (JpushUtils.isConnected(App.getInstance().getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            JpushUtils.showToast(logs, App.getInstance().getApplicationContext());
        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(App.getInstance().getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    Log.d(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(App.getInstance().getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}
