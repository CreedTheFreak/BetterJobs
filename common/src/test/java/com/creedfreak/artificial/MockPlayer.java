package com.creedfreak.artificial;

import com.creedfreak.common.container.IPlayer;
import com.creedfreak.common.professions.Profession;
import com.creedfreak.common.professions.TableType;

import java.util.*;

public class MockPlayer implements IPlayer {
	private final UUID mPlayerUUID;
	private final String mPlayerUsername;
	private final Integer mPlayerLevel;
	private final Long mPlayerDBID;
	private HashMap<TableType, Profession> mProfessionMap;

	public MockPlayer (Long pDBID, Integer pLevel, UUID pUUID, String pUsername) {
		mPlayerDBID = pDBID;
		mPlayerUUID = pUUID;
		mPlayerLevel = pLevel;
		mPlayerUsername = pUsername;

		mProfessionMap = new HashMap<> ();
	}

	public MockPlayer (Long pDBID, Integer pLevel, UUID pUUID, String pUsername, List<Profession> pProfList) {
		mPlayerDBID = pDBID;
		mPlayerUUID = pUUID;
		mPlayerLevel = pLevel;
		mPlayerUsername = pUsername;

		for (Profession prof : pProfList) {
			mProfessionMap.put (prof.type (), prof);
		}
	}

	public UUID getUUID () {
		return mPlayerUUID;
	}

	public String getUsername () {
		return mPlayerUsername;
	}

	public Integer getLevel () {
		return mPlayerLevel;
	}

	public Long getInternalID () {
		return mPlayerDBID;
	}

	public void sendMessage (String message) {
		// Swallow inputs can only be tested with a server api attached
	}

	public boolean checkPerms (String perm) {
		// Swallow the check, can't test unless we have a front end.
		return false;
	}

	public float payoutPlayerPool () {
		return 0F;
	}

	public boolean registerProfession (Profession prof) {
		boolean bIsPresent = mProfessionMap.containsKey (prof.type ());
		mProfessionMap.putIfAbsent (prof.type (), prof);
		return bIsPresent;
	}

	public void registerProfession (List<Profession> profList) {
		for (Profession prof : profList) {
			mProfessionMap.putIfAbsent (prof.type (), prof);
		}
	}

	public boolean unregisterProfession (TableType type) {
		mProfessionMap.remove (type);
		return !mProfessionMap.containsKey (type);
	}

	public void listProfessions () {
		// Swallow the functionality, this isn't needed.
	}

	public Collection<Profession> getProfessionCollection () {
		return mProfessionMap.values ();
	}

	public Profession getProfession (TableType type) {
		return mProfessionMap.get (type);
	}

	public void doWork (String elementName) {
		// Don't need this right now.
	}
}
