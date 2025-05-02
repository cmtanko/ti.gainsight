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
        Log.d(LCAT, "TiGainsightModule constructor called");
    }

    @Kroll.onAppCreate
    public static void onAppCreate(TiApplication app) {
        Log.d(LCAT, "onAppCreate called");
    }

    @Kroll.method
    public void init(String productId, String host, @Kroll.argument(optional = true) KrollFunction callback) {
        Log.d(LCAT, "init called with productId: " + productId + ", host: " + host);
        Log.d(LCAT, "callback is " + (callback != null ? "provided" : "null"));
        
        try {
            builder = new GainsightPX.Builder(TiApplication.getAppRootOrCurrentActivity(), productId)
                    .tag(productId)
                    .pxHost(host) 
                    .shouldTrackTapEvents(true)
                    .recordScreenViews(true);
            
            Log.d(LCAT, "Builder created successfully");
            
            GainsightPX instance = builder.build();
            Log.d(LCAT, "Instance built successfully");
            
            GainsightPX.setSingletonInstance(instance);
            Log.d(LCAT, "Singleton instance set successfully");
            Log.d(LCAT, "GainsightPX Instance: " + instance);

            if (callback != null) {
                KrollDict response = new KrollDict();
                response.put("initialized", true);
                response.put("instance", instance.toString());
                Log.d(LCAT, "Calling callback with success response");
                callback.callAsync(getKrollObject(), response);
            } else {
                Log.e(LCAT, "Callback not defined");
            }
        } catch (Exception e) {
            Log.e(LCAT, "Error in init: " + e.getMessage(), e);
            if (callback != null) {
                KrollDict errorResponse = new KrollDict();
                errorResponse.put("error", e.getMessage());
                callback.callAsync(getKrollObject(), errorResponse);
            }
        }
    }

    @Kroll.method
    public void enable(@Kroll.argument(optional = true) KrollFunction callback) {
        Log.d(LCAT, "enable called");
        Log.d(LCAT, "callback is " + (callback != null ? "provided" : "null"));
        
        try {
            Log.d(LCAT, "Setting GainsightPX enabled to true");
            GainsightPX.with().setEnable(true);
            
            Log.d(LCAT, "Enabling engagements");
            GainsightPX.with().enableEngagements(true);

            if (callback != null) {
                KrollDict response = new KrollDict();
                response.put("enabled", true);
                Log.d(LCAT, "Calling callback with success response");
                callback.callAsync(getKrollObject(), response);
            }
        } catch (Exception e) {
            Log.e(LCAT, "Error enabling GainsightPX: " + e.getMessage(), e);

            if (callback != null) {
                KrollDict errorResponse = new KrollDict();
                errorResponse.put("error", e.getMessage());
                Log.d(LCAT, "Calling callback with error response");
                callback.callAsync(getKrollObject(), errorResponse);
            }
        }
    }

    @Kroll.method
    public void screen(String screen) {
        Log.d(LCAT, "screen called with screen: " + screen);
        try {
            GainsightPX.with().screen(screen);
            Log.d(LCAT, "Screen event tracked successfully");
        } catch (Exception e) {
            Log.e(LCAT, "Error tracking screen event: " + e.getMessage(), e);
        }
    }

    @Kroll.method
    public void identify(KrollDict userdata, @Kroll.argument(optional = true) KrollFunction callback) {
        Log.d(LCAT, "identify called with userdata: " + userdata);
        Log.d(LCAT, "callback is " + (callback != null ? "provided" : "null"));
        try {
            String[] requiredParams = {
                "userId", "accountId", "identifyId", "title", "plan", 
                "sfdcid", "customSKUKey", "customSKUValue",
                "appversion", "userauthmethod", "userlicensetype", "userrole"
            };

            StringBuilder missingParams = new StringBuilder();
            for (String param : requiredParams) {
                if (!userdata.containsKey(param) || userdata.getString(param) == null || userdata.getString(param).isEmpty()) {
                    if (missingParams.length() > 0) {
                        missingParams.append(", ");
                    }
                    missingParams.append(param);
                }
            }

            if (missingParams.length() > 0) {
                throw new IllegalArgumentException("Missing required parameters: " + missingParams.toString());
            }

            String userId = userdata.getString("userId");
            Log.d(LCAT, "Creating user with ID: " + userId);
            User user = new User(userId);

            user.putAccountId(userdata.getString("accountId"));
            user.putIdentifyIdHash(userdata.getString("identifyId"));

            Map<String, Object> userAttributes = new HashMap<>();
            userAttributes.put("appversion", userdata.getString("appversion"));
            userAttributes.put("userauthmethod", userdata.getString("userauthmethod"));
            userAttributes.put("userlicensetype", userdata.getString("userlicensetype"));
            userAttributes.put("userrole", userdata.getString("userrole"));

            user.putCustomAttributes(userAttributes);

            String accountId = userdata.getString("title");
            Log.d(LCAT, "Creating account with ID: " + accountId);
            String plan = userdata.getString("plan");

            Account account = new Account(accountId);
            account.putName(userdata.getString("title"));
            account.putPlan(userdata.getString("plan"));
            account.putSfdcContactId(userdata.getString("sfdcid"));
            Map<String, Object> accountAttributes = new HashMap<>();
            accountAttributes.put(userdata.getString("customSKUKey"), userdata.getString("customSKUValue"));


            account.putCustomAttributes(accountAttributes);

            Log.d(LCAT, "Calling identify with user and account");
            GainsightPX.with().identify(user, account);
            Log.d(LCAT, "Identify call completed successfully");

            if (callback != null) {
                KrollDict successResponse = new KrollDict(); 
                successResponse.put("success", true);
                successResponse.put("message", "User and account identified successfully");
                Log.d(LCAT, "Calling callback with success response");
                callback.callAsync(getKrollObject(), successResponse);
            }
        } catch (Throwable tr) {
            Log.e(LCAT, "Error identifying user and account: " + tr.getMessage(), tr);

            if (callback != null) {
                KrollDict errorResponse = new KrollDict();
                errorResponse.put("success", false);
                errorResponse.put("error", tr.getMessage());
                Log.d(LCAT, "Calling callback with error response");
                callback.callAsync(getKrollObject(), errorResponse);
            }
        }
    }
    @Kroll.method
    public void crashApp(@Kroll.argument(optional = true) String type) {
        Log.d(LCAT, "crashApp called with type: " + type);
        if ("native".equals(type)) {
            Log.d(LCAT, "Triggering native crash");
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            Log.d(LCAT, "Triggering JavaScript crash");
            TiApplication.getInstance().getCurrentActivity().runOnUiThread(() -> {
                throw new RuntimeException("Intentional JavaScript crash for testing");
            });
        }
    }
}
