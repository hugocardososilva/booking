function jivo_onChangeState(state) {
	if (state == 'chat') {
		// widget is in the chat state
	}
	if (state == 'call' || state == 'chat/call') {
		// callback form is opened
		if (state == 'label' || state == 'chat/min') {
			// widget is minimized
		}
	}
}