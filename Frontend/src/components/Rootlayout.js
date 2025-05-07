import { Outlet } from "react-router-dom";
import MainNavigation from "./MainNavigation";

export default function RootLayout() {
  return (
    <div>
      <MainNavigation />
      <Outlet />
    </div>
  );
}
