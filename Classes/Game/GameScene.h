#pragma once
#include "Util.h"

class Block;

class GameScene : public Scene
{
public:
	static GameScene *createWithLevel(int level, int chapter);

CC_CONSTRUCTOR_ACCESS:
	GameScene() :
		m_nChapter(0),
		m_nLevel(0),
		m_playArea(0, 0, 0, 0) {}

	virtual bool initWithLevel(int level, int chapter);
	virtual bool init();
	virtual void onEnterTransitionDidFinish();

	void initBlocks();
	void onBlockBegan(Ref *sender, Touch *touch, Event *event);
	void onBlockMoved(Ref *sender, Touch *touch, Event *event);
	void onBlockEnded(Ref *sender, Touch *touch, Event *event);

private:
	int m_nChapter;
	int m_nLevel;
	Vector<Block *> m_blocks;
	Rect m_playArea;
};

