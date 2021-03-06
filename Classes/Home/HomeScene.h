#pragma once

#include "Util.h"
#include "../Common/BaseScene.h"

class HomeScene : public BaseScene
{
public:
    virtual bool init();
	virtual void onEnterTransitionDidFinish();
    
    // implement the "static create()" method manually
    CREATE_FUNC(HomeScene);

private:
	Label *labelNom;
	Label *labelNum;
	ScalableSprite *m_share;
	ScalableSprite *m_about;
    ScalableSprite *m_gamecenter;
    ScalableSprite *m_exit;
};

