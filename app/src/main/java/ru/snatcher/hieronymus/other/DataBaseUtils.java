package ru.snatcher.hieronymus.other;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import ru.snatcher.hieronymus.model.db.DaoMaster;
import ru.snatcher.hieronymus.model.db.DaoSession;

/**
 * {@link DataBaseUtils}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class DataBaseUtils {

	/**
	 * @param pContext - context for DaoMaster
	 * @return {@link DaoSession} to save DB data
	 */
	public static DaoSession getDaoSession(Context pContext) {

		DaoMaster.DevOpenHelper lvDevOpenHelper = new DaoMaster.DevOpenHelper(pContext, "app_database");
		Database lvWritableDb = lvDevOpenHelper.getWritableDb();
		return new DaoMaster(lvWritableDb).newSession();
	}
}
