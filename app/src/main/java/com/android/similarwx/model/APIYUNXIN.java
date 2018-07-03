package com.android.similarwx.model;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.inteface.YCallBack;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/22.
 */

public class APIYUNXIN {
    /**
     * 申请加入该群
     * @param teamId
     * @param postscript
     * @param callBack
     */
    public static void applyJoinTeam(String teamId, String postscript, YCallBack<Team> callBack){
        NIMClient.getService(TeamService.class).applyJoinTeam(teamId, postscript).setCallback(new RequestCallback<Team>() {
            @Override
            public void onSuccess(Team team) {
                // 申请成功, 等待验证入群
                if (callBack!=null)
                    callBack.callBack(team);
            }

            @Override
            public void onFailed(int code) {
                // 申请失败
                Toaster.toastShort(code+"");
            }

            @Override
            public void onException(Throwable exception) {
                // error
                Toaster.toastShort(exception.toString());
            }
        });
    }

    /**
     * 同意申请入群
     * @param teamId
     * @param account
     * @param callBack
     */
    public static void passApply(String teamId,String account,YCallBack<Void> callBack){
        // teamId为申请加入的群组id， account为申请入群的用户id

        NIMClient.getService(TeamService.class).passApply(teamId, account).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                // 成功
                if (callBack!=null)
                    callBack.callBack(param);
            }

            @Override
            public void onFailed(int code) {
                // 失败
                Toaster.toastShort(code+"");
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
                Toaster.toastShort(exception.toString());
            }
        });
    }

    /**
     * 拒绝入群申请
     * @param teamId
     * @param account
     * @param callBack
     */
    public static void rejectApply(String teamId,String account,YCallBack<Void> callBack){
        // teamId为申请加入的群组id， account为申请入群的用户id
        NIMClient.getService(TeamService.class).rejectApply(teamId, account,"您已被拒绝").setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                // 成功
                if (callBack!=null)
                    callBack.callBack(param);
            }

            @Override
            public void onFailed(int code) {
                // 失败
                Toaster.toastShort(code+"");
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
                Toaster.toastShort(exception.toString());
            }
        });
    }
    /**
     * 踢人出群
     * @param teamId
     * @param account
     * @param callBack
     */
    // teamId表示群ID，account表示被踢出的成员帐号
    public static void  removeMember(String teamId,String account,YCallBack<Void> callBack){
        NIMClient.getService(TeamService.class).removeMember(teamId, account).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                // 成功
                if (callBack!=null)
                    callBack.callBack(param);
            }

            @Override
            public void onFailed(int code) {
                // 失败
                Toaster.toastShort(code+"");
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
                Toaster.toastShort(exception.toString());
            }
        });
    }

    /**
     * 解散该群
     * @param teamId
     * @param callBack
     */
    public static void dismissTeam(String teamId,YCallBack<Void> callBack){
        NIMClient.getService(TeamService.class).dismissTeam(teamId).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                // 成功
                if (callBack!=null)
                    callBack.callBack(param);
            }

            @Override
            public void onFailed(int code) {
                // 失败
                Toaster.toastShort(code+"");
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
                Toaster.toastShort(exception.toString());
            }
        });
    }

    /**
     * 提升管理员
     * @param teamId
     * @param accountList
     * @param callBack
     */
    public static void addManagers(String teamId,List<String> accountList,YCallBack<List<TeamMember>> callBack){
        // teamId 操作的群id， accountList为待提升为管理员的用户帐号列表
        NIMClient.getService(TeamService.class).addManagers(teamId, accountList).setCallback(new RequestCallback<List<TeamMember>>() {
            @Override
            public void onSuccess(List<TeamMember> managers) {
                // 添加群管理员成功
                if (callBack!=null)
                    callBack.callBack(managers);
            }

            @Override
            public void onFailed(int code) {
                // 添加群管理员失败
                Toaster.toastShort(code+"");
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
                Toaster.toastShort(exception.toString());
            }
        });
    }

    /**
     * 禁言
     * @param teamId
     * @param account
     * @param mute  true为禁言、false为解禁
     * @param callBack
     */
    public static void muteTeamMember(String teamId,String account,boolean mute,YCallBack<Void> callBack){
        NIMClient.getService(TeamService.class).muteTeamMember(teamId, account, mute).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                // 成功
                if (callBack!=null)
                    callBack.callBack(param);
            }

            @Override
            public void onFailed(int code) {
                // 失败
                Toaster.toastShort(code+"");
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
                Toaster.toastShort(exception.toString());
            }
        });
    }
    public static void getTeamNotice(YCallBack<List<SystemMessage>> callBack){
        // 从1条开始，查询1条系统消息
        NIMClient.getService(SystemMessageService.class).querySystemMessages(0, 5)
                .setCallback(new RequestCallback<List<SystemMessage>>() {
                    @Override
                    public void onSuccess(List<SystemMessage> param) {
                        // 查询成功
                        if (callBack!=null)
                            callBack.callBack(param);
                    }

                    @Override
                    public void onFailed(int code) {
                        // 查询失败
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // error
                    }
                });
    }
}
