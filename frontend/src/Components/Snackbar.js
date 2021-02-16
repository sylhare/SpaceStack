import React, {PureComponent} from 'react';

export default class Snackbar extends PureComponent {
  message = '';
  mounted = false;
  state = {
    isActive: false,
  };

  openSnackBar = (message = 'Something went wrong...') => {
    this.message = message;
    this.activeTimer = this.setState({isActive: true}, () => {
      setTimeout(() => {
        if (this.mounted) this.setState({isActive: false});
      }, 3000);
    });
  };

  componentWillUnmount() {
    clearInterval(this.activeTimer);
  }

  render() {
    const {isActive} = this.state;
    return (
      <div className='snackbar-container'>
        <div className={isActive ? ['snackbar', 'show'].join(' ') : 'snackbar'}>
          {this.message}
        </div>
      </div>
    )
  }
}
