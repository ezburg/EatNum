#pragma once
#include "Util.h"

typedef std::function<void(Ref *, Touch *, Event *)> touchCallback;

class Block : public Node
{
public:
	static Block* create(const Color4B& bgColor, 
		const string& title, 
		const touchCallback& onTouchEnd,
		const Color4B& titleColor = Color4B::WHITE, 
		const cocos2d::Size& size = cocos2d::Size(100, 100), 
		const bool interactive = true, 
		const bool shader = true,
		const touchCallback& onTouchBegan = nullptr,
		const touchCallback& onTouchMoved = nullptr,
		const touchCallback& onTouchCancelled = nullptr);

	const std::string &getTitle() const 
	{
		return this->m_title->getString(); 
	}

	virtual void setTitle(const string &newTitle) 
	{
		this->m_title->setString(newTitle);
	}

	void setBgColor(Color4B &color);
	void lock(bool addLocker = false);
	void unlock();
	void setSkipped();

CC_CONSTRUCTOR_ACCESS:
	Block() : 
		m_bg(nullptr), 
		m_title(nullptr), 
		m_shader(nullptr), 
		m_card(nullptr),
		m_listener(nullptr),
		m_locker(nullptr),
		m_skipper(nullptr),
		m_bClicked(false), 
		m_bInteractive(true),
		m_oldZOrder(0) {}
	virtual ~Block() {}

	virtual bool init(const Color4B& bgColor, 
		const string& title, 
		const touchCallback& onTouchEnd,
		const Color4B& titleColor, 
		const cocos2d::Size& size, 
		const bool interactive, 
		const bool shader,
		const touchCallback& onTouchBegan,
		const touchCallback& onTouchMoved,
		const touchCallback& onTouchCancelled);

private:
	Sprite *m_bg;
	Label *m_title;
	Sprite *m_shader;
	Node *m_card;
	bool m_bClicked;
	bool m_bInteractive;
	int m_oldZOrder;
	EventListener *m_listener;
	Sprite *m_locker;
	Sprite *m_skipper;
	CC_DISALLOW_COPY_AND_ASSIGN(Block);
};

