import { useNavigate } from "react-router";
import logo from "../assets/kroitales-logo.png";

export default function NavBar() {
  const navigate = useNavigate();
  const go = (e, to) => {
    e.preventDefault();
    navigate(to);
  };

  return (
    <header>
      <nav className="navbar" aria-label="Primary">
        <a href="#" className="navbar__brand" onClick={(e) => go(e, "/")}>
          <img
            src={logo}
            alt="KroiTales logo: moon with sleeping baby"
            className="navbar__logo"
          />
          <span className="navbar__brandText">KroiTales</span>
        </a>

        <ul className="navbar__links">
          <li>
            <a href="#" className="navbar__link" onClick={(e) => go(e, "/")}>
              Home
            </a>
          </li>
          <li>
            <a
              href="#"
              className="navbar__link"
              onClick={(e) => go(e, "/about")}
            >
              About
            </a>
          </li>
        </ul>
      </nav>
    </header>
  );
}
