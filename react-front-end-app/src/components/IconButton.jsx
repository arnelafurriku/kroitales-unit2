function IconButton({ icon, children, className = "", ...props }) {
  const classes = ["btn", "icon-btn", className].filter(Boolean).join(" ");

  return (
    <button className={classes} {...props}>
      {icon && (
        <span className="icon-btn__icon" aria-hidden="true">
          {icon}
        </span>
      )}
      <span>{children}</span>
    </button>
  );
}
export default IconButton;
