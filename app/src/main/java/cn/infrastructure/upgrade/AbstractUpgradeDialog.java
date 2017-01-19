package cn.infrastructure.upgrade;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.infrastructure.ui.QuestionAlertDialog;

import java.io.File;

import cn.infrastructure.downloader.bizs.DLInfo;
import cn.infrastructure.downloader.bizs.DLManager;
import cn.infrastructure.downloader.interfaces.IDListener;

/**
 * Created by Frank on 2016/8/11.
 */
/* package */ abstract class AbstractUpgradeDialog extends QuestionAlertDialog {

    public static final int EXTERNAL_STORAGE_NOT_AVAILABLE = -999;
    public static final int EXTERNAL_STORAGE_SPACE_NOT_ENOUGH = -998;

    private static final long DOWNLOAD_MIN_FREE_SPACE = 100 * 1024 * 1024;

    private String mDownloadUrl;

    protected TextView mTvUpgradeContent;
    protected ProgressBar mPbDownload;

    public AbstractUpgradeDialog(Context context) {
        super(context);
        initDownloadDialogUI(context);
    }

    private void initDownloadDialogUI(Context context) {
        LinearLayout layoutContent = new LinearLayout(context);
        layoutContent.setOrientation(LinearLayout.VERTICAL);

        //content text view
        TextView tvUpgradeContent = new TextView(context);
        mTvUpgradeContent = tvUpgradeContent;
        tvUpgradeContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.45f);
        tvUpgradeContent.setTextColor(Color.parseColor("#333333"));
        tvUpgradeContent.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams FrankTvUpgradeContent = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        FrankTvUpgradeContent.topMargin = dp2px(45.8f);
        FrankTvUpgradeContent.bottomMargin = dp2px(26.2f);
        layoutContent.addView(tvUpgradeContent, FrankTvUpgradeContent);

        //progress bar
        ProgressBar pb = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mPbDownload = pb;
        mPbDownload.setVisibility(View.GONE);

        GradientDrawable pbBgDrawable = new GradientDrawable();
        pbBgDrawable.setColor(Color.parseColor("#d9d9d9"));
        pbBgDrawable.setCornerRadius(8);

        GradientDrawable progressDrawable = new GradientDrawable();
        progressDrawable.setColor(Color.parseColor("#ff5400"));
        progressDrawable.setCornerRadius(8);
        ClipDrawable pbProgressClipDrawable = new ClipDrawable(progressDrawable,
                Gravity.LEFT, ClipDrawable.HORIZONTAL);

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]
                {pbBgDrawable, pbProgressClipDrawable});
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.progress);

        pb.setProgressDrawable(layerDrawable);
        LinearLayout.LayoutParams FrankPb = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp2px(4.36f));
        int horizonMargin = dp2px(30);
        FrankPb.leftMargin = horizonMargin;
        FrankPb.rightMargin = horizonMargin;
        FrankPb.bottomMargin = dp2px(20);
        layoutContent.addView(pb, FrankPb);

        pb.setMax(100);
        pb.setProgress(0);

        super.setContent(layoutContent);
        super.setCanCancelByUser(false);
        super.mBtnRight.setText(UpgradeText.TEXT_UPGRADE);
        super.mBtnRight.setTextColor(Color.parseColor("#ff5400"));
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;

        if (!TextUtils.isEmpty(downloadUrl)) {
            DLInfo dlInfo = DLManager.getInstance(getContext().getApplicationContext()).getCompleteInfo(downloadUrl);

            if (dlInfo != null && dlInfo.currentBytes == dlInfo.totalBytes
                    && !TextUtils.isEmpty(dlInfo.dirPath) && !TextUtils.isEmpty(dlInfo.fileName)) {

                File file = new File(dlInfo.dirPath + dlInfo.fileName);
                if (file.exists() && file.length() == dlInfo.currentBytes) {
                    mBtnRight.setText(UpgradeText.TEXT_INSTALL);
                }
            }
        }
    }

    public final String getDownloadUrl() {
        return mDownloadUrl;
    }

    /**
     * download the file
     * @param downloadUrl the request download url
     * @param fileName download file name, if null, downloader will try to get file name from server,
     *                 if failed from server, use {@code UUID.randonUUID().toString() } as file name
     * @param listener the download listener
     */
    protected final void download(String downloadUrl, String fileName, IDListener listener) {
        String fileSaveDir;
        File externalFilesDir = getContext().getExternalFilesDir(null);
        if (externalFilesDir != null && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (externalFilesDir.getFreeSpace() < DOWNLOAD_MIN_FREE_SPACE) {
                showToast(UpgradeText.TEXT_DOWNLOAD_SPACE_NOT_ENOUGH);

                if (listener != null) {
                    listener.onError(EXTERNAL_STORAGE_SPACE_NOT_ENOUGH, "");
                }
                return;
            }

            fileSaveDir = externalFilesDir.getAbsolutePath() + "/upgrade/";

        } else {
            if (listener != null) {
                listener.onError(EXTERNAL_STORAGE_NOT_AVAILABLE, "");
            }
            return;
        }

        DLManager.getInstance(getContext().getApplicationContext()).dlStart(downloadUrl, fileSaveDir, fileName, listener);
    }

    protected final void installApp(String apkPath) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkPath), "application/vnd.android.package-archive");
        getContext().startActivity(intent);
    }

    //make sure call on ui thread
    protected final void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
