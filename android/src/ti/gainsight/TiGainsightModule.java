package ti.gainsight;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollObject;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.TiApplication;

import com.gainsight.px.mobile.GainsightPX;
import com.gainsight.px.mobile.User;
import com.gainsight.px.mobile.Account;

@Kroll.module(name = "TiGainsight", id = "ti.gainsight")
public class TiGainsightModule extends KrollModule {
    private static final String LCAT = "TiGainsightModule";
    private static final boolean DBG = TiConfig.LOGD;
    private GainsightPX.Builder builder;

    public TiGainsightModule() {
        super();
    }

    @Kroll.onAppCreate
    public static void onAppCreate(TiApplication app) {
        Log.d(LCAT, "inside onAppCreate");
    }

    @Kroll.method
    public void init(String productId, @Kroll.argument(optional = true) KrollFunction callback) {
        builder = new GainsightPX.Builder(TiApplication.getAppRootOrCurrentActivity(), productId)
                .tag(productId)
                .shouldTrackTapEvents(true)
                .recordScreenViews(true);
        
        Log.d("GainsightPX", "LOGS");
        GainsightPX instance = builder.build();
        GainsightPX.setSingletonInstance(instance);
        Log.d(LCAT, "GainsightPX initialized with productId: " + productId);

        if (callback != null) {
            KrollDict response = new KrollDict();
            response.put("initialized", true);
            response.put("instance", instance.toString());
            callback.callAsync(getKrollObject(), response);
        }
    }

    @Kroll.method
    public void enable(@Kroll.argument(optional = true) KrollFunction callback) {
        try {
            GainsightPX.with().setEnable(true);
            GainsightPX.with().enableEngagements(true);

            if (callback != null) {
                KrollDict response = new KrollDict();
                response.put("enabled", true);
                callback.callAsync(getKrollObject(), response);
            }
        } catch (Exception e) {
            Log.e(LCAT, "Error enabling GainsightPX", e);

            if (callback != null) {
                KrollDict errorResponse = new KrollDict();
                errorResponse.put("error", e.getMessage());
                callback.callAsync(getKrollObject(), errorResponse);
            }
        }
    }

    @Kroll.method
    public void screenEvent(String screen) {
        Log.d(LCAT, "Tracking screen event: " + screen);
        GainsightPX.with().screen(screen);
    }

    @Kroll.method
    public void identify(KrollDict userdata, @Kroll.argument(optional = true) KrollFunction callback) {
        try {
            String userId = userdata.getString("userId");
            User user = new User(userId);

            user.putEmail(userdata.getString("email"));
            user.putFirstName(userdata.getString("firstName"));
            user.putLastName(userdata.getString("lastName"));
            user.putCountryCode(userdata.getString("countryCode"));
            user.putTimeZone(userdata.getString("timeZone"));
            user.putRole(userdata.getString("role"));

			String accountId = userdata.getString("title");
			Account account = new Account(accountId);
			account.putName(userdata.getString("title"));

            GainsightPX.with().identify(user, account);

            if (callback != null) {
                KrollDict successResponse = new KrollDict();
                successResponse.put("message", "User and account identified successfully");
                callback.callAsync(getKrollObject(), successResponse);
            }
        } catch (Throwable tr) {
            Log.e(LCAT, "Error identifying user and account", tr);

            if (callback != null) {
                KrollDict errorResponse = new KrollDict();
                errorResponse.put("error", tr.getMessage());
                callback.callAsync(getKrollObject(), errorResponse);
            }
        }
    }

    @Kroll.method
    public void crashApp(@Kroll.argument(optional = true) String type) {
        if ("native".equals(type)) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            TiApplication.getInstance().getCurrentActivity().runOnUiThread(() -> {
                throw new RuntimeException("Intentional JavaScript crash for testing");
            });
        }
    }
}
