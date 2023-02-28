package br.com.the475group.diagnosticar.modelo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import br.com.the475group.diagnosticar.R;

public class Notificacao {

	// METODO PARA GERAR A NOTIFICAÇÃO
	public static void geraNotificacao(Context context, Intent it, String aviso, String titulo, String mensagem) { // http://stackoverflow.com/questions/6391870/how-exactly-to-use-notification-builder
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification n = new Notification(R.drawable.ic_launcher, aviso,
				System.currentTimeMillis());

		// Se usuário selecionar notificação, abre activity
		PendingIntent p = PendingIntent.getActivity(context, 1, it, 0);

		// vinculando intent com notificação
		n.setLatestEventInfo(context, titulo, mensagem, p); 

		n.defaults |= Notification.DEFAULT_LIGHTS;
		n.defaults |= Notification.DEFAULT_SOUND;

		nm.notify(R.string.app_name, n);
	}

	// METODO PARA CANCELAR A NOTIFICACAO
	public static void cancelaNotificacao(Context context) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(1);
	}

}
