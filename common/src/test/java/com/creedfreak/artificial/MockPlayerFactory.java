package com.creedfreak.artificial;

import com.creedfreak.common.container.IPlayer;
import com.creedfreak.common.container.IPlayerFactory;
import com.creedfreak.common.professions.Profession;

import java.util.List;
import java.util.UUID;

public class MockPlayerFactory implements IPlayerFactory {
	public IPlayer buildPlayer (Long dbID, Integer playerLevel, UUID playerUUID, String username) {

		return new MockPlayer (dbID, playerLevel, playerUUID, username);
	}

	public IPlayer buildPlayerWithProfessions (Long playerID, Integer playerLevel,
	                                           UUID playerUUID, String username,
	                                           List<Profession> professions) {

		return new MockPlayer (playerID, playerLevel, playerUUID, username, professions);
	}
}
